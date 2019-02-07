package com.example.mycloudapp;

public class Data {
    public String totalUsage;
    public CloudProviderData[] cloudUsageList;
    public boolean isContainProvider(String name) {
        if(cloudUsageList != null && cloudUsageList.length > 0) {
            for(int i = 0;i<cloudUsageList.length;i++){
                if(cloudUsageList[i].accountType != null && cloudUsageList[i].accountType.toUpperCase().equals(name.toUpperCase())){
                    return true;
                }
            }
        }
        return false;
    }

    public String getBalanceInformation(String name) {
        if(cloudUsageList != null && cloudUsageList.length > 0) {
            for(int i = 0;i<cloudUsageList.length;i++){
                if(cloudUsageList[i].accountType != null && cloudUsageList[i].accountType.toUpperCase().equals(name.toUpperCase())){
                    return name.toUpperCase() + "    Accounts balance:"+cloudUsageList[i].accountsBalance;
                }
            }
        }
        return "";
    }
    public CloudProviderData getProviderDataByType(String type) {
        for(int i = 0;i<cloudUsageList.length;i++){
            if(cloudUsageList[i].accountType != null && cloudUsageList[i].accountType.toUpperCase().equals(type.toUpperCase())){
                return cloudUsageList[i];
            }
        }
        return null;
    }
}
