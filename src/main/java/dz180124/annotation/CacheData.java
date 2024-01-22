package dz180124.annotation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CacheData{
    private final String methodName;
    private final List<Map.Entry<Class<?>,Object>> identity;

    public CacheData(String methodName, List<Map.Entry<Class<?>,Object>> identity) {
        this.methodName = methodName;
        this.identity = identity;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof CacheData){
            CacheData data = (CacheData) obj;

            if(identity.size()!=data.identity.size()
                    || !methodName.equals(data.methodName))
                return false;

            for(int i = 0; i < identity.size(); i++){
                Map.Entry<Class<?>,Object> thisIdentity = identity.get(i);
                Map.Entry<Class<?>,Object> dataIdentity = data.identity.get(i);
                if(!thisIdentity.getKey().equals(dataIdentity.getKey())
                || !thisIdentity.getValue().equals(dataIdentity.getValue()))
                    return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return methodName.hashCode() + identity.stream().mapToInt(x->x.getKey().hashCode() + x.getValue().hashCode()).sum();
    }
}
