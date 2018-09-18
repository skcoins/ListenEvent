package com.softisland.model;

import java.io.Serializable;
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
@Table(name = "exchange_coins")
public class ExchangeCoins implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "transcation_hash")
    private String transcationHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "from_person")
    private String fromPerson;

    @Column(name = "to_person")
    private String toPerson;

    private Long nums;

    //0:eth  1:token 2 积分
    private Short currency;

    @Column(name = "event_name")
    private String eventName;

    private Short status;

    private Long gas;

    @Column(name = "confirm_block_num")
    private Long confirmBlockNum;

    @Column(name = "is_call")
    private Short isCall;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "call_date")
    private Date callDate;

    private static final long serialVersionUID = 1L;
}