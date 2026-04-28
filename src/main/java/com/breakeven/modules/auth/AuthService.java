package com.breakeven.modules.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.breakeven.modules.tenant.Tenant;
import com.breakeven.modules.tenant.TenantRepository;
import com.breakeven.modules.user.User;
import com.breakeven.modules.user.UserRepository;
import com.breakeven.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       TenantRepository tenantRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        if (tenantRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já registado");
        }

        Tenant tenant = new Tenant();
        tenant.setNome(request.getNomeLoja());
        tenant.setEmail(request.getEmail());
        tenantRepository.save(tenant);

        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTenant(tenant);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), tenant.getId());
        return new AuthResponse(token, user.getNome(), user.getEmail(), tenant.getId(), tenant.getPlano());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getTenant().getId());
        return new AuthResponse(token, user.getNome(), user.getEmail(),
                user.getTenant().getId(), user.getTenant().getPlano());
    }
}