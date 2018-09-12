package com.softisland.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transcation_event")
public class TranscationEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_number")
    private Long blockNumber;

    //交易HASH
    @Column(name = "transcation_hash")
    private String transcationHash;

    //块HASH
    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "from_person")
    private String fromPerson;

    @Column(name = "to_person")
    private String toPerson;

    private Long nums;

    private Short currency;

    //交易状态 0初始化，1交易成功 2交易失败
    private Short status;

    private Long gas;

    @Column(name = "confirm_block_number")
    private Long confirmBlockNumber;

    //是否回调  0否 1是
    @Column(name = "is_call")
    private Short isCall;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "call_date")
    private Date callDate;
    
    @Column(name = "event_name")
    private String eventName;

    private static final long serialVersionUID = 1L;

}