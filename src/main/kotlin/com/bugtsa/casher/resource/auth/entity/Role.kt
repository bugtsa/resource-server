package com.bugtsa.casher.resource.auth.entity;

import javax.persistence.*

@Entity
class Role : BaseIdEntity() {

    var name: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "permission_role", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "permission_id", referencedColumnName = "id")])
    var permissions: List<Permission>? = null

}