package in.testuniversity.service.impl;

import org.springframework.stereotype.Service;

import in.testuniversity.entity.Role;
import in.testuniversity.repository.RoleRepository;
import in.testuniversity.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	private final RoleRepository roleRepository;
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role findByRole(String role) {
		return roleRepository.findByRole(role);
	}

}
