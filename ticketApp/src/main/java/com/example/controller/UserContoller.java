package com.example.controller;

import com.example.dto.UserDTO;
import com.example.dto.request.UpdatePasswordRequest;
import com.example.dto.request.UserUpdateRequest;
import com.example.dto.response.ODResponse;
import com.example.dto.response.ResponseMessage;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserContoller {

    private UserService userService;

    /**
     * Admin bütün kullanıları listeler.
     * @return UserDTO tipinde bir list döner.
     */
    @GetMapping("/auth/all")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.findAllUsers();

        return ResponseEntity.ok(users);
    }

    /**
     * Hali hazırda giriş yapmış kullanici bilgilerini getirir.
     * @param request Hali hazırda giriş yapmış olan kullanıcının, token'a atanan id'si alınarak repo'dan bilgilerini çeker.
     * @return UserDTO olarak döner.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INDIVIDUAL') or hasRole('CORPORATE')")
    public ResponseEntity<UserDTO> getUserById(HttpServletRequest request){
        Long id = (Long) request.getAttribute("id");
        UserDTO userDTO = userService.findById(id);

        return ResponseEntity.ok(userDTO);
    }

    /**
     *Sayfalanmış şekilde kullanıcıları listeler
     * @param page kaçıncı sayfayı getireceğimi endpointte belirttiğim değişken
     * @param size bir sayfada kaç adet user geliceğini belirttiğim yer
     * @param prop hangi variable göre sıralama yapıcağımı belittiğim yer
     * @param direction sıralama şeklim (ASC, DESC)
     * @return UserDTO tipinde page döner.
     */
    @GetMapping("/auth/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getAllUsersPage(@RequestParam("page")int page,
                                                         @RequestParam("size")int size,
                                                         @RequestParam("sort")String prop,
                                                         @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));

        Page<UserDTO> userDTOPage=userService.findUserPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }

    /**
     * Admin kullanici, herhangi bir kullanicinin bilgilerini getirebilir.
     * @param id Service tarafında map edilmiş UserDTO id'si.
     * @return UserDTO döner.
     */
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByIdAdmin(@PathVariable Long id){
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Hali hazırda giriş yapmış kullanıcı şifresini değiştirebilir
     * @param httpServletRequest hali hazırda giriş yapmış kullanıcı
     * @param passwordRequest kullanıcadan şifre değiştirmek için aldığım request sinifim
     * @return
     */
    @PatchMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INDIVIDUAL') or hasRole('CORPORATE')")
    public ResponseEntity<ODResponse> updatePassword(HttpServletRequest httpServletRequest,
                                                     @RequestBody UpdatePasswordRequest passwordRequest){
        Long id = (Long) httpServletRequest.getAttribute("id");
        userService.updatePassword(id, passwordRequest);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.PASSWORD_CHANGED_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    /**
     * Kullanıcı bilgileri update edildi
     * @param httpServletRequest Hali hazırda giriş yapmış kullanıcının id'si alındı
     * @param userUpdateRequest Update olucak kullanıcı bilgilerini formatladığım User dto sınıfım.
     * @return
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INDIVIDUAL') or hasRole('CORPORATE')")
    public ResponseEntity<ODResponse> updateUser(HttpServletRequest httpServletRequest, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        Long id = (Long) httpServletRequest.getAttribute("id");
        userService.updateUser(id, userUpdateRequest);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    /**
     * Admin kullanıcı silebilir
     * @param id path'ten gelen id, Admin'in silmek istediği user id'dir
     * @return İşlem'in başarı ile yapıldığını döner
     */
    @DeleteMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ODResponse> deleteUser(@PathVariable Long id){
        userService.removeById(id);

        ODResponse response = new ODResponse();
        response.setMessage(ResponseMessage.DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

}
