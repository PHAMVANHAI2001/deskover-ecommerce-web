package com.deskover.controller.rest.api.dashboard;

import com.deskover.model.entity.database.Administrator;
import com.deskover.model.entity.dto.AdminCreateDto;
import com.deskover.model.entity.dto.AdministratorDto;
import com.deskover.model.entity.dto.ChangePasswordDto;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.other.util.ValidationUtil;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class AdministratorApi {
	@Autowired
	AdminService adminService;

	@Autowired
	AdminAuthorityService adminAuthorityService;

	@GetMapping("/users")
	public ResponseEntity<?> doGetIsActived(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("isActive") Optional<Boolean> isActive) {
		Page<Administrator> Admins = adminService.getByActived(isActive.orElse(Boolean.TRUE), page.orElse(0),
				size.orElse(1));
		if (Admins.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
		}
		return ResponseEntity.ok(Admins);
	}

	@GetMapping("/users/actived")
    public ResponseEntity<?> doGetAllActive() {
        List<Administrator> admins = adminService.getByActived(Boolean.TRUE);
        if (admins.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
        }
        return ResponseEntity.ok(admins);
    }
	
	@PostMapping("/users/datatables")
	public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input,
			@RequestParam("isActive") Optional<Boolean> isActive) {
		return ResponseEntity.ok(adminService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> doGetProfile(@PathVariable("id") Long id) {
		try {
			Administrator admin = adminService.getById(id);
			if (admin == null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Administrator not found"));
			}
			return ResponseEntity.ok(admin);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
		}
		
	
	}

	@PostMapping("/users")
	public ResponseEntity<?> doCreate(@Valid @RequestBody AdminCreateDto admin, BindingResult result) {
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			AdministratorDto adminCreated = adminService.create(admin);
			return ResponseEntity.ok().body(adminCreated);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/users")
	public ResponseEntity<?> doUpdate(@Valid @RequestBody AdministratorDto admin, BindingResult result) {
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			AdministratorDto adminUpdated = adminService.update(admin);
			return ResponseEntity.ok().body(adminUpdated);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/users/password")
	public ResponseEntity<?> doUpdatePassword(@Valid @RequestBody ChangePasswordDto admin, BindingResult result) {
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			AdministratorDto adminUpdated = adminService.updatePassword(admin);
			return ResponseEntity.ok().body(adminUpdated);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
		try {
			adminService.changeActived(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping("/users/authority")
	public ResponseEntity<?> doChangeRole(@RequestParam(value = "adminId", required = true) Long adminId,
			@RequestParam(value = "roleId", required = true) Long roleId) {
		try {
			adminAuthorityService.changeRole(adminId, roleId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

}
