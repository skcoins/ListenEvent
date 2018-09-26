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
@Table(name = "bonus_event")
public class BonusEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "transcation_hash")
    private String transcationHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "trade_person")
    private String tradePerson;

    @Column(name = "event_name")
    private String eventName;

    private Short status;

    private String gas;

    @Column(name = "confirm_block_number")
    private Long confirmBlockNumber;

    @Column(name = "is_call")
    private Short isCall;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "call_date")
    private Date callDate;

    private String referrer;

    @Column(name = "referrer_token")
    private String referrerToken;

    @Column(name = "token_holder")
    private String tokenHolder;

    private static final long serialVersionUID = 1L;


}