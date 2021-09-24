package com.sbs.untactTeacher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untactTeacher.dao.AdmArticleDao;
import com.sbs.untactTeacher.dao.ArticleDao;
import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Board;
import com.sbs.untactTeacher.dto.Member;
import com.sbs.untactTeacher.dto.ResultData;

@Service
public class AdmArticleService {
	@Autowired
	private AdmArticleDao admArticleDao;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemberService memberService;

	public static String IsAccepted(Article article) {
        switch (article.getIsAccepted()) {
            case 1:
                return "승인대기";
            case 0:
                return "승인";
            case -1:
                return "반려";
            default:
                return "";
        }
    }
	
	public static boolean AcceptStatus(Article article) {
        if ( article.getIsAccepted() == -1 ) {
            return false;
        }
        return article.getIsAccepted() == 0;
    }
	
	public ResultData modifyArticle(int id, String title, String body) {
		Article article = getArticleById(id);

		if (isEmpty(article)) {
			return new ResultData("F-1", "존재하지 않는 게시물 번호입니다.", "id", id);
		}

		articleDao.modifyArticle(id, title, body);

		return new ResultData("S-1", "게시물이 수정되었습니다.", "id", id);
	}

	private boolean isEmpty(Article article) {
		if (article == null) {
			return true;
		} else if (article.isDelStatus()) {
			return true;
		}

		return false;
	}

	public ResultData deleteArticleById(int id) {
		Article article = getArticleById(id);

		if (isEmpty(article)) {
			return new ResultData("F-1", "게시물이 존재하지 않습니다.", "id", id);
		}

		articleDao.deleteArticleById(id);

		return new ResultData("S-1", id + "번 게시물이 삭제되었습니다.", "id", id, "boardId", article.getBoardId());
	}


	
	public ResultData writeArticle(int boardId, int memberId, String title, String body, Member actor) {
		admArticleDao.writeArticle( boardId, memberId, title, body, actor);
		int id = admArticleDao.getLastInsertId();
		
		
		
		return new ResultData("S-1", "게시물이 작성되었습니다. ","id", id, "boardId", boardId);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public Board getBoardById(int id) {
		return articleDao.getBoardById(id);
	}

	public int getArticlesTotalCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		return admArticleDao.getArticlesTotalCount(boardId, searchKeywordTypeCode, searchKeyword);
	}
	
	public List<Article> getForPrintArticles(Article article, Member actor, int boardId, String searchKeywordTypeCode, String searchKeyword,
			int itemsCountInAPage, int page) {
		
		AdmArticleService.AcceptStatus(article);
		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		if (actor.getAuthLevel() == 7) {
			return ;
		}
		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		return admArticleDao.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword, limitFrom, limitTake);
	}

	public List<Article> getForPrintCheck(int boardId, String searchKeywordTypeCode, String searchKeyword,
			int itemsCountInAPage, int page) {
		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		
		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		return admArticleDao.getForPrintCheck(boardId, searchKeywordTypeCode, searchKeyword, limitFrom, limitTake);
	}

	public Article getForPrintArticleById(int id) {
		return articleDao.getForPrintArticleById(id);
	}
}
