package org.patsimas.chat.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.users.MyUserDetails;
import org.patsimas.chat.dto.users.UserDto;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final ConversionService conversionService;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		log.info("Load user process [email: {}] start", email);

		Optional<User> user = userRepository.findByEmail(email);

		if(user.isPresent()){

			UserDto userDto = conversionService.convert(user.get(), UserDto.class);
			if (!ObjectUtils.isEmpty(userDto))
				userDto.setPassword(user.get().getPassword());

			log.info("Load user process completed");

			return new MyUserDetails(userDto);
		}

		user.orElseThrow(() -> new UsernameNotFoundException("Not found " + email));

		return null;
	}

}
