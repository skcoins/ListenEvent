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
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class Bankroll_sol_BankRoll extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160208061088c833981016040525160008054600160a060020a0319908116331790915560018054600160a060020a039093169290911691909117905561082d8061005f6000396000f3006080604052600436106100ae5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630bb3a06681146100b35780631e9a69501461010a5780632a0daeea1461014257806347e201831461016957806356d597771461018a5780636b931cad146101df5780638da5cb5b14610206578063c21a231514610237578063d5a01ffc1461025b578063d731f34214610270578063f3fef3a31461037a575b600080fd5b3480156100bf57600080fd5b50604080516020600480358082013583810280860185019096528085526101089536959394602494938501929182918501908490808284375094975061039e9650505050505050565b005b34801561011657600080fd5b5061012e600160a060020a0360043516602435610415565b604080519115158252519081900360200190f35b34801561014e57600080fd5b50610157610457565b60408051918252519081900360200190f35b34801561017557600080fd5b50610157600160a060020a036004351661045d565b34801561019657600080fd5b5060408051602060048035808201358381028086018501909652808552610108953695939460249493850192918291850190849080828437509497506104789650505050505050565b3480156101eb57600080fd5b50610108600160a060020a03600435811690602435166104eb565b34801561021257600080fd5b5061021b61053d565b60408051600160a060020a039092168252519081900360200190f35b34801561024357600080fd5b5061012e600160a060020a036004351660243561054c565b34801561026757600080fd5b5061021b6105a5565b34801561027c57600080fd5b5060408051602060046024803582810135848102808701860190975280865261010896843596369660449591949091019291829185019084908082843750506040805187358901803560208181028481018201909552818452989b9a998901989297509082019550935083925085019084908082843750506040805187358901803560208181028481018201909552818452989b9a99890198929750908201955093508392508501908490808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506105b49650505050505050565b34801561038657600080fd5b5061012e600160a060020a036004351660243561078e565b60008054600160a060020a031633146103b657600080fd5b5060005b81518110156104115760006003600084848151811015156103d757fe5b602090810291909101810151600160a060020a03168252810191909152604001600020805460ff19169115159190911790556001016103ba565b5050565b60008183600160a060020a03167fe497990cbcdad216d0cee0a47bdd46928c6ec37dcefc927dc4bd3c16e623a86d60405160405180910390a350600192915050565b60045481565b600160a060020a031660009081526002602052604090205490565b60008054600160a060020a0316331461049057600080fd5b5060005b81518110156104115760016003600084848151811015156104b157fe5b602090810291909101810151600160a060020a03168252810191909152604001600020805460ff1916911515919091179055600101610494565b600054600160a060020a0316331461050257600080fd5b600160a060020a03918216600090815260036020526040808220805460ff199081169091559290931681529190912080549091166001179055565b600054600160a060020a031681565b600154600090600160a060020a0316331461056657600080fd5b6040518290600160a060020a038516907fe497990cbcdad216d0cee0a47bdd46928c6ec37dcefc927dc4bd3c16e623a86d90600090a350600192915050565b600154600160a060020a031681565b60008054600160a060020a03163314806105dd57503360009081526003602052604090205460ff165b15156105e857600080fd5b83518551146105f657600080fd5b825184511461060457600080fd5b50600485905560005b845181101561078657828181518110151561062457fe5b9060200190602002015160026000878481518110151561064057fe5b90602001906020020151600160a060020a0316600160a060020a03168152602001908152602001600020819055507f8fece7425d1df78407c08e77378d4912cb50cbd965d2f9ee8efb9a3cf5573ed786868381518110151561069e57fe5b9060200190602002015186848151811015156106b657fe5b9060200190602002015186858151811015156106ce57fe5b90602001906020020151866040518086815260200185600160a060020a0316600160a060020a0316815260200184815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610740578181015183820152602001610728565b50505050905090810190601f16801561076d5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a160010161060d565b505050505050565b60008054600160a060020a03163314806107b757503360009081526003602052604090205460ff165b15156107c257600080fd5b6040518290600160a060020a038516907f87d5f4772963d1f9b76047158b4ae97c420a1b3bff2a746c828beffd9e7c3e2690600090a3506001929150505600a165627a7a723058201cf4537dd62e5977eb7d467c2007d81ded195b7f8e5b5cae6f0a61ef29f5305d0029";

    public static final String FUNC_UNSETADMINISTRATOR = "unsetAdministrator";

    public static final String FUNC_REDEEM = "redeem";

    public static final String FUNC_SERIALNUMBER = "serialNumber";

    public static final String FUNC_POINT = "point";

    public static final String FUNC_SETADMINISTRATOR = "setAdministrator";

    public static final String FUNC_REPLACEADMINISTRATOR = "replaceAdministrator";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ETH_REDEEM = "eth_redeem";

    public static final String FUNC_SKCADDRESS = "skcAddress";

    public static final String FUNC_UPDATELEDGER = "updateLedger";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event REDEEMEVENT_EVENT = new Event("redeemEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event WITHDRAWEVENT_EVENT = new Event("withdrawEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event LEDGERRECORDEVENT_EVENT = new Event("ledgerRecordEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected Bankroll_sol_BankRoll(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Bankroll_sol_BankRoll(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> unsetAdministrator(List<String> _administrators) {
        final Function function = new Function(
                FUNC_UNSETADMINISTRATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_administrators, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> redeem(String _caller, BigInteger _amount) {
        final Function function = new Function(
                FUNC_REDEEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_caller), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> serialNumber() {
        final Function function = new Function(FUNC_SERIALNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> point(String who) {
        final Function function = new Function(FUNC_POINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setAdministrator(List<String> _administrators) {
        final Function function = new Function(
                FUNC_SETADMINISTRATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_administrators, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> replaceAdministrator(String oldOwner, String newOwner) {
        final Function function = new Function(
                FUNC_REPLACEADMINISTRATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(oldOwner), 
                new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> eth_redeem(String _caller, BigInteger _amount) {
        final Function function = new Function(
                FUNC_ETH_REDEEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_caller), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> skcAddress() {
        final Function function = new Function(FUNC_SKCADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> updateLedger(BigInteger _serialNumber, List<String> _address, List<BigInteger> _oldPionts, List<BigInteger> _newPoints, String datetime) {
        final Function function = new Function(
                FUNC_UPDATELEDGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_serialNumber), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_address, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_oldPionts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_newPoints, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.Utf8String(datetime)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdraw(String _caller, BigInteger _amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_caller), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Bankroll_sol_BankRoll> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _skcAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_skcAddress)));
        return deployRemoteCall(Bankroll_sol_BankRoll.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Bankroll_sol_BankRoll> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _skcAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_skcAddress)));
        return deployRemoteCall(Bankroll_sol_BankRoll.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<RedeemEventEventResponse> getRedeemEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REDEEMEVENT_EVENT, transactionReceipt);
        ArrayList<RedeemEventEventResponse> responses = new ArrayList<RedeemEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RedeemEventEventResponse typedResponse = new RedeemEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RedeemEventEventResponse> redeemEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RedeemEventEventResponse>() {
            @Override
            public RedeemEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REDEEMEVENT_EVENT, log);
                RedeemEventEventResponse typedResponse = new RedeemEventEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RedeemEventEventResponse> redeemEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REDEEMEVENT_EVENT));
        return redeemEventEventObservable(filter);
    }

    public List<WithdrawEventEventResponse> getWithdrawEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWEVENT_EVENT, transactionReceipt);
        ArrayList<WithdrawEventEventResponse> responses = new ArrayList<WithdrawEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventEventResponse typedResponse = new WithdrawEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawEventEventResponse> withdrawEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawEventEventResponse>() {
            @Override
            public WithdrawEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWEVENT_EVENT, log);
                WithdrawEventEventResponse typedResponse = new WithdrawEventEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<WithdrawEventEventResponse> withdrawEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWEVENT_EVENT));
        return withdrawEventEventObservable(filter);
    }

    public List<LedgerRecordEventEventResponse> getLedgerRecordEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LEDGERRECORDEVENT_EVENT, transactionReceipt);
        ArrayList<LedgerRecordEventEventResponse> responses = new ArrayList<LedgerRecordEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LedgerRecordEventEventResponse typedResponse = new LedgerRecordEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._serialNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._oldPiont = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._newPoint = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.date = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LedgerRecordEventEventResponse> ledgerRecordEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, LedgerRecordEventEventResponse>() {
            @Override
            public LedgerRecordEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LEDGERRECORDEVENT_EVENT, log);
                LedgerRecordEventEventResponse typedResponse = new LedgerRecordEventEventResponse();
                typedResponse.log = log;
                typedResponse._serialNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._oldPiont = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._newPoint = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.date = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<LedgerRecordEventEventResponse> ledgerRecordEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LEDGERRECORDEVENT_EVENT));
        return ledgerRecordEventEventObservable(filter);
    }

    public static Bankroll_sol_BankRoll load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bankroll_sol_BankRoll(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Bankroll_sol_BankRoll load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bankroll_sol_BankRoll(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class RedeemEventEventResponse {
        public Log log;

        public String sender;

        public BigInteger amount;
    }

    public static class WithdrawEventEventResponse {
        public Log log;

        public String sender;

        public BigInteger amount;
    }

    public static class LedgerRecordEventEventResponse {
        public Log log;

        public BigInteger _serialNumber;

        public String _address;

        public BigInteger _oldPiont;

        public BigInteger _newPoint;

        public String date;
    }
}
