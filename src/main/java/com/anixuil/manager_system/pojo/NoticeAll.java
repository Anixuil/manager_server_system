package com.anixuil.manager_system.pojo;

import com.anixuil.manager_system.entity.AttachmentTable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoticeAll {
    private String noticeInfoUuid;
    private String noticeInfoTitle;
    private String noticeInfoIntro;
    private String noticeInfoContent;
    private String noticeInfoType;
//    private String attachmentUuid;
//    private String attachmentName;
//    private String attachmentHref;
    private List<AttachmentTable> attachmentList;

}
