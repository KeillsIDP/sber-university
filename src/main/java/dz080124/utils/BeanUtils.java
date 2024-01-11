package dz080124.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        Method[] toMethods = to.getClass().getMethods();
        List<Method> setters = new ArrayList<>();
        addMethodsByString("set",setters,toMethods,1);

        Method[] fromMethods = from.getClass().getMethods();
        List<Method> getters = new ArrayList<>();
        addMethodsByString("get",getters,fromMethods,0);

        for (int i=0;i<setters.size();i++) {
            Method currentSetter = setters.get(i);
            String setterName = ReflectionUtils.deleteFirstLetters(3,currentSetter.getName().chars());
            for (Method getter: getters) {
                String getterName = ReflectionUtils.deleteFirstLetters(3,getter.getName().chars());
                if(currentSetter.getParameterTypes()[0].equals(getter.getReturnType())
                    && setterName.equals(getterName)){
                    try {
                        Object getValue = getter.invoke(from);
                        currentSetter.invoke(to,getValue);
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    } catch (InvocationTargetException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        }
    }
    private static void addMethodsByString(String str,List<Method> to,Method[] from,int parameterCount){
        for (Method method: from) {
            if(method.getParameterCount()!=parameterCount)
                continue;
            String seq = ReflectionUtils.getFirstLetters(3,method.getName().chars());
            if(seq.equals(str))
                to.add(method);
        }
    }
}

