package com.softisland.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Small_sol_small extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161069a38038061069a83398101604090815281516020808401519284015191840180519094939093019261004e9160009190860190610099565b508151610062906001906020850190610099565b50600381905560048054600160a060020a031916339081179091556000600581905590815260076020526040902055506101349050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100da57805160ff1916838001178555610107565b82800160010185558215610107579182015b828111156101075782518255916020019190600101906100ec565b50610113929150610117565b5090565b61013191905b80821115610113576000815560010161011d565b90565b610557806101436000396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166323b872dd81146100bb5780633c3c9c23146100f9578063438f486f146101205780635fce90661461013557806370a08231146101595780637bc008381461017a5780638da5cb5b1461018f5780639829ab8e146101c0578063a9059cbb146101eb578063bd8716b71461020f578063cb05b93e14610236575b6100b8333461024b565b50005b3480156100c757600080fd5b506100e5600160a060020a036004358116906024351660443561026c565b604080519115158252519081900360200190f35b34801561010557600080fd5b5061010e610308565b60408051918252519081900360200190f35b34801561012c57600080fd5b5061010e61030e565b34801561014157600080fd5b506100e5600160a060020a036004351660243561024b565b34801561016557600080fd5b5061010e600160a060020a036004351661032c565b34801561018657600080fd5b5061010e610347565b34801561019b57600080fd5b506101a461034d565b60408051600160a060020a039092168252519081900360200190f35b3480156101cc57600080fd5b506101d561035c565b6040805160ff9092168252519081900360200190f35b3480156101f757600080fd5b506100e5600160a060020a0360043516602435610365565b34801561021b57600080fd5b506100e5600160a060020a036004351660243560010b6103b5565b34801561024257600080fd5b5061010e61050e565b60045460009061026590600160a060020a0316848461026c565b9392505050565b600160a060020a03831660009081526007602052604081205482111561029157600080fd5b600160a060020a0380851660008181526007602090815260408083208054889003905593871680835291849020805487019055835192835282015280820184905290517fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9181900360600190a15060019392505050565b60055481565b600454600160a060020a031660009081526006602052604090205490565b600160a060020a031660009081526007602052604090205490565b60035481565b600454600160a060020a031681565b60025460ff1681565b3360009081526007602052604081205482111561038157600080fd5b503360009081526007602052604080822080548490039055600160a060020a03841682529020805482019055600192915050565b60006103bf610514565b600160a060020a038416600090815260076020526040902054600111156103e557600080fd5b50600160a060020a03808416600081815260076020908152604080832080546000190190556004548516835260068252808320805460019081019091558151808301835285815288820b818501818152600980548086018255975282517f6e1540171b6c0c960b71a7020d9f60077f6af931a8bbf590da0223dacf75c7af90970180549151850b61ffff16740100000000000000000000000000000000000000000275ffff00000000000000000000000000000000000000001998909a1673ffffffffffffffffffffffffffffffffffffffff1990921691909117969096169790971790945581519485529490940b90830152825190927f9c2b0fa2bb0f138ae07692cfc8126e3b78a6516f3a6b330e43a2b99b6d620d03928290030190a15060019392505050565b60055490565b6040805180820190915260008082526020820152905600a165627a7a72305820849d09d4fe19c111b16ccf54231172c2ad60f60f0b17c1c2a7cc5c4a3d1886330029";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TOTALETH = "totalEth";

    public static final String FUNC_GETGOLDPOOL = "getGoldPool";

    public static final String FUNC_BUYSMALL = "buySmall";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_SMALLTOTALSUPPLY = "smallTotalSupply";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SMALLDECIMALS = "smallDecimals";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_BUYCODE = "buyCode";

    public static final String FUNC_GETETH = "getEth";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CODECHANGE_EVENT = new Event("CodeChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Int16>() {}));
    ;

    protected Small_sol_small(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Small_sol_small(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalEth() {
        final Function function = new Function(FUNC_TOTALETH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getGoldPool() {
        final Function function = new Function(FUNC_GETGOLDPOOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> buySmall(String buyer, BigInteger _value) {
        final Function function = new Function(
                FUNC_BUYSMALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(buyer), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> smallTotalSupply() {
        final Function function = new Function(FUNC_SMALLTOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> smallDecimals() {
        final Function function = new Function(FUNC_SMALLDECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> buyCode(String _buyer, BigInteger _code) {
        final Function function = new Function(
                FUNC_BUYCODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_buyer), 
                new org.web3j.abi.datatypes.generated.Int16(_code)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getEth() {
        final Function function = new Function(FUNC_GETETH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Small_sol_small> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String name, String symbol, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(symbol), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(Small_sol_small.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Small_sol_small> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String name, String symbol, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(symbol), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(Small_sol_small.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<CodeChangeEventResponse> getCodeChangeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CODECHANGE_EVENT, transactionReceipt);
        ArrayList<CodeChangeEventResponse> responses = new ArrayList<CodeChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CodeChangeEventResponse typedResponse = new CodeChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CodeChangeEventResponse> codeChangeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, CodeChangeEventResponse>() {
            @Override
            public CodeChangeEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CODECHANGE_EVENT, log);
                CodeChangeEventResponse typedResponse = new CodeChangeEventResponse();
                typedResponse.log = log;
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.code = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<CodeChangeEventResponse> codeChangeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CODECHANGE_EVENT));
        return codeChangeEventObservable(filter);
    }

    public static Small_sol_small load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Small_sol_small(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Small_sol_small load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Small_sol_small(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public BigInteger _value;
    }

    public static class CodeChangeEventResponse {
        public Log log;

        public String buyer;

        public BigInteger code;
    }
}
