package com.example.coresample.widget.listview.sectionheader;

import java.util.List;

/**
 * Created by tigris on 2017-08-22.
 * 작성자 : 황의택
 * 내용 : SectionHeaderAdapter에서 사용하는 자식을 포함한 헤더 객체
 */
public interface SectionHeaderObj {
    /**
     * 해당 Section의 자식객체를 반환
     * @param <E> 자식 객체의 타입
     * @return
     */
    <E> List<E> getChild();
}
