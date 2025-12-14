package com.kb.healthcare.user.adapter.out.persistence;

import com.kb.healthcare.user.application.port.out.CreateUserPort;
import com.kb.healthcare.user.application.port.out.FindUserPort;

interface UserRepository extends FindUserPort, CreateUserPort {

}
