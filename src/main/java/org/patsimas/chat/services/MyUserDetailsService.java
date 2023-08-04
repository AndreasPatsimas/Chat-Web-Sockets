package org.patsimas.chat.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.dto.users.MyUserDetails;
import org.patsimas.chat.dto.users.UserDto;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("Load user process [username: {}] start", username);

		Optional<User> user = userRepository.findByUserName(username);

		if(user.isPresent()){
			log.info("Load user process completed");
			MyUserDetails myUserDetails = new MyUserDetails(user.get());
			return myUserDetails;
		}

		user.orElseThrow(() -> new UsernameNotFoundException("Not found " + username));

		return null;
	}
}
