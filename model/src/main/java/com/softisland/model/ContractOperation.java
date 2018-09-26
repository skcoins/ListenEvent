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
@Table(name = "contract_operation")
public class ContractOperation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "business_id")
    private Long businessId; 

    private String name;

    private Long gas;

    private Short status;

    @Column(name = "transcation_hash")
    private String transcationHash;

    @Column(name = "block_hash")
    private String blockHash;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "operation_person")
    private String operationPerson;

    @Column(name = "to_person")
    private String toPerson;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    private String data;

    private static final long serialVersionUID = 1L;

}