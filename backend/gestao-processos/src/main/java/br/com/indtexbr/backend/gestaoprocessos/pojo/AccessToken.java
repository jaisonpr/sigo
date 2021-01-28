package br.com.indtexbr.backend.gestaoprocessos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessToken {

	private String access_token;
	private String token_type;
	private String not_before_policy;
	private String session_state;
	private String scope;
}
