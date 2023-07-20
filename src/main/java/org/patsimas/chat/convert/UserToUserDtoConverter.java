package org.patsimas.chat.convert;

import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.users.UserDto;
import org.patsimas.chat.enums.ActiveStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

	@Override
	public UserDto convert(User user) {

		return UserDto.builder()
				.id(user.getId())
				.active(user.getActive() == ActiveStatus.ACTIVE.code())
				.password(user.getPassword())
				.email(user.getEmail())
				.authorities(buildAuthorities(user))
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.build();
	}

	private String buildAuthorities(User user){

		StringJoiner authorities = new StringJoiner(",");

		user.getAuthorities().forEach(authority -> authorities.add(authority.getDescription().toString()));
		return authorities.toString();
	}

}
