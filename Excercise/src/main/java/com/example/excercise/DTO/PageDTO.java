package com.example.excercise.DTO;


import lombok.Getter;
import lombok.Setter;

//페이징처리를 위한 DTO
@Getter
@Setter
public class PageDTO {
    private Long page;
    private Long postPerPage;
    private Long totalPage;
    private Long startRow;
    private Long lastRow;
    private Long nowPageNumber;
    private Long pageNumber;
    private Long startPageNumber;
    private Long lastPageNumber;
    private boolean previous;
    private boolean next;

    //현재페이지, 한 페이지에 몇 개의 게시물, 하나의 블럭에 몇 개의 페이지
    public PageDTO(){
        this.page = 1L;
        this.postPerPage = 8L;
        this.pageNumber = 5L;
    }

    public Long getPage(){
        if(this.page == null){
            this.page = 1L;
        }else if(this.page < 1){
            this.page = 1L;
        }
        return page;
    }

    public Long getPostPerPage(){
        if(this.postPerPage == null){
            this.postPerPage = 8L;
        }
        return postPerPage;
    }

    //각 페이지별 첫번째 글과 마지막 글
    public void setRow(){
        this.startRow = (this.getPage()-1)*this.getPostPerPage()+1;
        this.lastRow = this.getPage()*this.getPostPerPage();
    }

    //전체 페이지 수와 전체 블럭 수
    public void setNum(Long totalCount){
        this.totalPage = (totalCount + this.getPostPerPage() - 1) /this.getPostPerPage();

        Long curBlock = (this.getPage() - 1) / this.getPageNumber() + 1;

        //블럭의 시작과 끝
        this.startPageNumber = (curBlock-1)*this.getPageNumber() + 1;
        this.lastPageNumber = Math.min(curBlock * this.getPageNumber(), totalPage);

        //현재 페이지가 2이상이면 이전 버튼 활성화
        this.previous = curBlock > 1;
        this.next = curBlock < (totalPage / this.getPageNumber() + (totalPage % this.getPageNumber() > 0 ? 1 : 0));
    }
}
