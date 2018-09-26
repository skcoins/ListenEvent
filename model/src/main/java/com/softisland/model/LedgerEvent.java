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
@Table(name = "ledger_event")
public class LedgerEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id")
    private Long businessId;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "transcation_hash")
    private String transcationHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "event_name")
    private String eventName;

    private Short status;

    private String gas;

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