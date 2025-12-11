package com.sentencefit.application.classjoin.port.out

import com.sentencefit.domain.classjoin.model.ClassMember

interface SaveClassMemberPort {
    fun save(classMember: ClassMember): ClassMember
}