package com.sbs.untactTeacher.service;

import com.sbs.untactTeacher.dao.ArticleDao;
import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Board;
import com.sbs.untactTeacher.dto.Member;
import com.sbs.untactTeacher.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private MemberService memberService;

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
		articleDao.writeArticle(boardId, memberId, title, body, actor);
		int id = articleDao.getLastInsertId();
		
		if (memberService.isAdmin(actor)) {
			return new ResultData("S-1", "게시물이 작성되었습니다. ","id", id, "boardId", boardId);
		}
		
		return new ResultData("S-2", "게시물이 작성되었습니다. "
				+ "작성하신 게시물은 1~2일의 기간의 검증 후 게시판에 업로드 됩니다.", "boardId", boardId, "id", id);
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

		return articleDao.getArticlesTotalCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public List<Article> getForPrintArticles(int boardId, String searchKeywordTypeCode, String searchKeyword,
			int itemsCountInAPage, int page) {
		if (searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		return articleDao.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword, limitFrom, limitTake);
	}

	public Article getForPrintArticleById(int id) {
		return articleDao.getForPrintArticleById(id);
	}
}
