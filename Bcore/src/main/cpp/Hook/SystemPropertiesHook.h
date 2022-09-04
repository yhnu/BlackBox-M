//
// Created by Milk on 4/25/21.
//

#ifndef BLACKBOX_SystemPropertiesHook_H
#define BLACKBOX_SystemPropertiesHook_H

#include <map>
#include "BaseHook.h"
#include <string>


class SystemPropertiesHook : public BaseHook{
public:
    static void init(JNIEnv *env);
//    static std::map<std::string , std::string > prop_map;
};

#endif //BLACKBOX_SystemPropertiesHook_H
