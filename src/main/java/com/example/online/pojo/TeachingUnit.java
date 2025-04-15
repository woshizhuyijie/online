package com.example.online.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TeachingUnit {
    private Long id;             // 教学单元ID
    private String unitName;     // 教学单元名称
    private String unitDescription; // 教学单元描述
    private Long courseId;       // 所属课程ID
    private Integer learningScore;//学习积分
    private Integer isChapter;//1为章节单元 0 为小节单元

    private Integer fatherId;//父亲单元id
   private   List<String> docUrls;
    private   List<String> imgUrls;
    private   List<String> videoUrls;

    // Getter 和 Setter 方法
}
