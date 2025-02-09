package com.senai.anchieta.api.auth.dto;

import com.senai.anchieta.api.auth.model.UserRoles;

public record RegisterDTO (String login, String password, UserRoles role){
}
