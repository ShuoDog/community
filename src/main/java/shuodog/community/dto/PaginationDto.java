package shuodog.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {
    private List<QuestionDto> questionDtoList;
    private List<Integer> pages = new ArrayList<>();
    private Integer currentPage;
    private Integer totalPage;
    private boolean showFirstPage;
    private boolean showPreviousPage;
    private boolean showNextPage;
    private boolean showEndPage;

    public void setPagination(Integer total, Integer currentPage, Integer size) {

        if(total%size==0){
            totalPage=total/size;
        }
        else {
            totalPage=total/size+1;
        }

        if(currentPage<1)currentPage=1;
        else if(currentPage>totalPage)currentPage=totalPage;
        this.currentPage=currentPage;

        pages.add(currentPage);
        for(int i=1;i<=3;i++)
        {
            if(currentPage-i>0)
            {
                pages.add(0,currentPage-i);
            }
            if(currentPage+i<=totalPage)
            {
                pages.add(currentPage+i);
            }
        }


        if(pages.contains(1)){
            showFirstPage=false;
        }
        else {
            showFirstPage=true;
        }

        if(currentPage==1){
            showPreviousPage=false;
        }
        else {
            showPreviousPage=true;
        }

        if (currentPage==totalPage){
            showNextPage=false;
        }
        else{
            showNextPage=true;
        }

        if (pages.contains(totalPage)){
            showEndPage=false;
        }
        else {
            showEndPage=true;
        }
    }


}
