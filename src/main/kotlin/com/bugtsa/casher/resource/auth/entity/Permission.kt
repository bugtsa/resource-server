package com.bugtsa.casher.resource.auth.entity;

import javax.persistence.Entity

@Entity
class Permission : BaseIdEntity() {

    var name: String? = null
}
