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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
    private static final String BINARY = "608060405234801561001057600080fd5b50604051602080610a20833981016040525160008054600160a060020a0319908116331790915560018054600160a060020a03909316929091169190911790556109c18061005f6000396000f3006080604052600436106100a05763ffffffff60e060020a60003504166304b5b5b681146100a55780630549f150146100e05780630bb3a066146101015780630bc32ab61461015857806347e201831461017f57806356d59777146101b25780636b931cad146102075780638da5cb5b1461022e578063b69a81071461025f578063bf20855914610280578063d5a01ffc1461034c578063e030bbd514610361575b600080fd5b3480156100b157600080fd5b506100cc600435600160a060020a036024351660443561037c565b604080519115158252519081900360200190f35b3480156100ec57600080fd5b506100cc600160a060020a036004351661048d565b34801561010d57600080fd5b5060408051602060048035808201358381028086018501909652808552610156953695939460249493850192918291850190849080828437509497506104c49650505050505050565b005b34801561016457600080fd5b506100cc600435600160a060020a036024351660443561053b565b34801561018b57600080fd5b506101a0600160a060020a0360043516610568565b60408051918252519081900360200190f35b3480156101be57600080fd5b5060408051602060048035808201358381028086018501909652808552610156953695939460249493850192918291850190849080828437509497506105839650505050505050565b34801561021357600080fd5b50610156600160a060020a03600435811690602435166105f6565b34801561023a57600080fd5b50610243610648565b60408051600160a060020a039092168252519081900360200190f35b34801561026b57600080fd5b50610156600160a060020a0360043516610657565b34801561028c57600080fd5b5060408051602060046024803582810135848102808701860190975280865261015696843596369660449591949091019291829185019084908082843750506040805187358901803560208181028481018201909552818452989b9a998901989297509082019550935083925085019084908082843750506040805187358901803560208181028481018201909552818452989b9a99890198929750908201955093508392508501908490808284375094975061069d9650505050505050565b34801561035857600080fd5b5061024361086f565b34801561036d57600080fd5b506100cc60043560243561087e565b600080548190600160a060020a03163314806103a757503360009081526003602052604090205460ff165b15156103b257600080fd5b600154604080517f7472616e7366657228616464726573732c75696e7432353629000000000000008152815190819003601901812063ffffffff60e060020a918290049081169091028252600160a060020a0388811660048401526024830188905292519290931692916044808301926000929190829003018183875af19350508215915061043f905057fe5b60408051868152602081018590528151600160a060020a038716927f81c292b70d9deca02d4e4aa3ffdb4797a8f96803a6f2e62b2ebb870903b35ef8928290030190a2506001949350505050565b60008054600160a060020a031633146104a557600080fd5b50600160a060020a031660009081526003602052604090205460ff1690565b60008054600160a060020a031633146104dc57600080fd5b5060005b81518110156105375760006003600084848151811015156104fd57fe5b602090810291909101810151600160a060020a03168252810191909152604001600020805460ff19169115159190911790556001016104e0565b5050565b600154600090600160a060020a0316331461055557600080fd5b610560848484610892565b949350505050565b600160a060020a031660009081526002602052604090205490565b60008054600160a060020a0316331461059b57600080fd5b5060005b81518110156105375760016003600084848151811015156105bc57fe5b602090810291909101810151600160a060020a03168252810191909152604001600020805460ff191691151591909117905560010161059f565b600054600160a060020a0316331461060d57600080fd5b600160a060020a03918216600090815260036020526040808220805460ff199081169091559290931681529190912080549091166001179055565b600054600160a060020a031681565b600054600160a060020a0316331461066e57600080fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60008054600160a060020a03163314806106c657503360009081526003602052604090205460ff165b15156106d157600080fd5b83516101f410156106e157600080fd5b82518451146106ef57600080fd5b81518351146106fd57600080fd5b5060005b835181101561075f57818181518110151561071857fe5b9060200190602002015160026000868481518110151561073457fe5b6020908102909101810151600160a060020a0316825281019190915260400160002055600101610701565b7fbb2f3391a5c9021982a64c2c35d7250435b574f09c0b27ed884f39a16fc7ac458585858560405180858152602001806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b838110156107d25781810151838201526020016107ba565b50505050905001848103835286818151815260200191508051906020019060200280838360005b838110156108115781810151838201526020016107f9565b50505050905001848103825285818151815260200191508051906020019060200280838360005b83811015610850578181015183820152602001610838565b5050505090500197505050505050505060405180910390a15050505050565b600154600160a060020a031681565b600061088b833384610892565b9392505050565b600154604080517f72656465656d47616d65506f696e747328616464726573732c2075696e74323581527f36290000000000000000000000000000000000000000000000000000000000006020820152815190819003602201812063ffffffff60e060020a9182900490811690910282523360048301526024820185905291516000938493600160a060020a0390911692909160448083019286929190829003018183875af193505082159150610947905057fe5b60408051868152602081018590528151600160a060020a038716927fa62dfe64893408597f284008da6ea2dabf0d823f951f46c27f3aba1d5494ce4f928290030190a25060019493505050505600a165627a7a723058209b7b7c8ad676d8a62cfe4fa3b3d9eb9bdf37f43d1daa2b1c4c4a8a21b988d4060029";

    public static final String FUNC_POINTTOTOKEN = "pointToToken";

    public static final String FUNC_GETADMINISTRATOR = "getAdministrator";

    public static final String FUNC_UNSETADMINISTRATOR = "unsetAdministrator";

    public static final String FUNC_TOKENTOPOINTBYSKCCONTRACT = "tokenToPointBySkcContract";

    public static final String FUNC_POINT = "point";

    public static final String FUNC_SETADMINISTRATOR = "setAdministrator";

    public static final String FUNC_REPLACEADMINISTRATOR = "replaceAdministrator";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETSKCADDERSS = "setSkcAdderss";

    public static final String FUNC_UPDATELEDGER = "updateLedger";

    public static final String FUNC_SKCADDRESS = "skcAddress";

    public static final String FUNC_TOKENTOPOINTBYMETAMASK = "tokenToPointByMetaMask";

    public static final Event TOKENTOPOINTEVENT_EVENT = new Event("tokenToPointEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event POINTTOTOKENEVENT_EVENT = new Event("pointToTokenEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LEDGERRECORDEVENT_EVENT = new Event("ledgerRecordEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
    ;

    protected Bankroll_sol_BankRoll(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Bankroll_sol_BankRoll(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> pointToToken(BigInteger _id, String _withdrawer, BigInteger _amount) {
        final Function function = new Function(
                FUNC_POINTTOTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id), 
                new org.web3j.abi.datatypes.Address(_withdrawer), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> getAdministrator(String adr) {
        final Function function = new Function(FUNC_GETADMINISTRATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(adr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> unsetAdministrator(List<String> _administrators) {
        final Function function = new Function(
                FUNC_UNSETADMINISTRATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_administrators, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> tokenToPointBySkcContract(BigInteger _id, String _recharger, BigInteger _amount) {
        final Function function = new Function(
                FUNC_TOKENTOPOINTBYSKCCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id), 
                new org.web3j.abi.datatypes.Address(_recharger), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteCall<TransactionReceipt> setSkcAdderss(String _skcAddress) {
        final Function function = new Function(
                FUNC_SETSKCADDERSS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_skcAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateLedger(BigInteger _id, List<String> _address, List<BigInteger> _oldPionts, List<BigInteger> _newPoints) {
        final Function function = new Function(
                FUNC_UPDATELEDGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(_address, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_oldPionts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(_newPoints, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> skcAddress() {
        final Function function = new Function(FUNC_SKCADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> tokenToPointByMetaMask(BigInteger _id, BigInteger _amount) {
        final Function function = new Function(
                FUNC_TOKENTOPOINTBYMETAMASK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id), 
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

    public List<TokenToPointEventEventResponse> getTokenToPointEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENTOPOINTEVENT_EVENT, transactionReceipt);
        ArrayList<TokenToPointEventEventResponse> responses = new ArrayList<TokenToPointEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenToPointEventEventResponse typedResponse = new TokenToPointEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._recharger = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TokenToPointEventEventResponse> tokenToPointEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TokenToPointEventEventResponse>() {
            @Override
            public TokenToPointEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENTOPOINTEVENT_EVENT, log);
                TokenToPointEventEventResponse typedResponse = new TokenToPointEventEventResponse();
                typedResponse.log = log;
                typedResponse._recharger = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TokenToPointEventEventResponse> tokenToPointEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENTOPOINTEVENT_EVENT));
        return tokenToPointEventEventObservable(filter);
    }

    public List<PointToTokenEventEventResponse> getPointToTokenEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(POINTTOTOKENEVENT_EVENT, transactionReceipt);
        ArrayList<PointToTokenEventEventResponse> responses = new ArrayList<PointToTokenEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PointToTokenEventEventResponse typedResponse = new PointToTokenEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PointToTokenEventEventResponse> pointToTokenEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, PointToTokenEventEventResponse>() {
            @Override
            public PointToTokenEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(POINTTOTOKENEVENT_EVENT, log);
                PointToTokenEventEventResponse typedResponse = new PointToTokenEventEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<PointToTokenEventEventResponse> pointToTokenEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POINTTOTOKENEVENT_EVENT));
        return pointToTokenEventEventObservable(filter);
    }

    public List<LedgerRecordEventEventResponse> getLedgerRecordEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LEDGERRECORDEVENT_EVENT, transactionReceipt);
        ArrayList<LedgerRecordEventEventResponse> responses = new ArrayList<LedgerRecordEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LedgerRecordEventEventResponse typedResponse = new LedgerRecordEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._address = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._oldPiont = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._newPoint = (List<BigInteger>) eventValues.getNonIndexedValues().get(3).getValue();
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
                typedResponse._id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._address = (List<String>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._oldPiont = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._newPoint = (List<BigInteger>) eventValues.getNonIndexedValues().get(3).getValue();
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

    public static class TokenToPointEventEventResponse {
        public Log log;

        public String _recharger;

        public BigInteger _id;

        public BigInteger _amount;
    }

    public static class PointToTokenEventEventResponse {
        public Log log;

        public String sender;

        public BigInteger _id;

        public BigInteger amount;
    }

    public static class LedgerRecordEventEventResponse {
        public Log log;

        public BigInteger _id;

        public List<String> _address;

        public List<BigInteger> _oldPiont;

        public List<BigInteger> _newPoint;
    }
}
