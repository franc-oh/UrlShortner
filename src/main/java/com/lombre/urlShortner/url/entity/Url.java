package com.lombre.urlShortner.url.entity;

import lombok.*;

import javax.persistence.*;

/**
 * TB_URL 엔티티
 *  ID
 *  ORIG_URL : 단축 전 URL
 *  CONVERT_URL : 변환 된 URL
 *  CONVERT_CNT : 변환 요청 수(디폴트=1)
 */

@Entity
@Table(name="TB_URL")
@Getter
@NoArgsConstructor
public class Url {

    @Id
    @SequenceGenerator(name="seq_id", sequenceName = "URL_SEQ", initialValue = 1000000001, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id")
    @Column(name="ID")
    private Long id;

    @Column(name="ORIG_URL", nullable = false)
    private String origUrl;

    @Column(name="CONVERT_URL")
    private String convertUrl;

    @Column(name="CONVERT_CNT", nullable = false)
    private Long convertCnt;

    @Builder
    public Url(String origUrl, String convertUrl, Long convertCnt) {
        this.origUrl = origUrl;
        this.convertUrl = convertUrl;
        this.convertCnt = convertCnt;
    }


    public void updateConvertUrl(String convertUrl) { this.convertUrl = convertUrl; }
    public void updateConvertCnt(Long convertCnt) { this.convertCnt = convertCnt; }
}
