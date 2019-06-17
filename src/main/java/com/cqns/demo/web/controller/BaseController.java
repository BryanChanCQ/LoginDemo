package com.cqns.demo.web.controller;

import com.cqns.demo.dao.entity.*;
import com.cqns.demo.utils.JwtTokenUtil;
import com.cqns.demo.utils.ResultInfo;
import com.cqns.demo.web.service.baseservice.*;
import com.cqns.demo.web.vo.*;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/BaseController")
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Resource
    private BaseService baseService;
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private RoleResourceService roleResourceService;
    @Resource
    private UserRoleService userRoleService;

    @Resource
    private BranchInfoService branchInfoService;
    
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public ResultInfo<List> userVoList(@RequestBody UserVo userVo){
        return ResultInfo.create(List.class).success(userService.userVoList(userVo));
    }

    @RequestMapping(value = "/userVoPageInfo", method = RequestMethod.POST)
    public ResultInfo<Map> userVoPageInfo(@RequestBody UserVo userVo) {
    	System.out.print("   ");
        return ResultInfo.create(Map.class).success(userService.userVoPageInfo(userVo));
    }

    @RequestMapping(value = {"/updateUserById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> updateUserById(@RequestBody User user){
        Preconditions.checkNotNull(user.getId(), "Id不能为空");
        try{
            return this.userService.updateUser(user) ?
                    ResultInfo.create().success().setMsg("更新成功") :
                    ResultInfo.create().fail("更新失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/deleteUserById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> deleteUserById(Long id){
        Preconditions.checkNotNull(id, "Id不能为空");
        try{
            return this.userService.deleteById(id) ?
                    ResultInfo.create().success().setMsg("删除成功") :
                    ResultInfo.create().fail("删除失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = "/menuVoPageInfo", method = RequestMethod.POST)
    public ResultInfo<Page> menuVoPageInfo(@RequestBody MenuVo menuVo){
        return ResultInfo.create(Page.class).success(menuService.menuVoPageInfo(menuVo));
    }

    @RequestMapping(value = "${jwt.route.login}", method = RequestMethod.GET)
    public ResultInfo<String> login(String username, String password) {
        return ResultInfo.create(String.class).success(baseService.login(username, password));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResultInfo<String> refresh(String token) {
        return ResultInfo.create(String.class).success(baseService.refreshToken(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultInfo<Void> userVo(@RequestBody User user){
        try{
            return userService.register(user) ?
                    ResultInfo.create().success().setMsg("注册成功") :
                    ResultInfo.create().fail("注册失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = "/queryCurrentUser", method = RequestMethod.GET)
    public ResultInfo<UserVo> queryCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResultInfo.create(UserVo.class).success(this.userService.queryUserDetailByName(userDetails.getUsername()));
    }

    @RequestMapping(value = {"/queryMenuByRoleIds"}, method = {RequestMethod.POST})
    public ResultInfo<List> queryMenuByRoleIds(@RequestBody List<Long> ids) {
        return ResultInfo.create(List.class).success(this.menuService.queryMenusByRoleIds(ids));
    }

    @RequestMapping(value = {"/queryRolePage"}, method = {RequestMethod.POST})
    public ResultInfo<Page> queryRolePage(@RequestBody RoleVo roleVo) {
        return ResultInfo.create(Page.class).success(this.roleService.roleVoPageInfo(roleVo));
    }

    @RequestMapping(value = {"/addRole"}, method = {RequestMethod.POST})
    public  ResultInfo<Void> addRole(@RequestBody Role role){
        try{
            return this.roleService.insert(role) ?
                    ResultInfo.create().success().setMsg("添加成功") :
                    ResultInfo.create().fail("添加失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/updateRoleById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> updateRoleById(@RequestBody Role role){
        Preconditions.checkNotNull(role.getId(), "Id不能为空");
        try{
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(role.getId());
            roleResource.setRoleName(role.getName());
            UserRole userRole = new UserRole();
            userRole.setRoleName(role.getName());
            userRole.setRoleId(role.getId());
            this.roleService.updateById(role);
            this.roleResourceService.updateRoleResource(roleResource);
            this.userRoleService.updateUserRole(userRole);
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
        return ResultInfo.create().success().setMsg("更新成功");
    }

    @RequestMapping(value = {"/deleteRoleById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> deleteRoleById(Long id){
        Preconditions.checkNotNull(id, "Id不能为空");
        try{
            return this.roleService.deleteById(id) ?
                    ResultInfo.create().success().setMsg("删除成功") :
                    ResultInfo.create().fail("删除失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = "/roleResourceVoPageInfo", method = RequestMethod.POST)
    public ResultInfo<Page> roleResourceVoPageInfo(@RequestBody RoleResourceVo roleResourceVo){
        return ResultInfo.create(Page.class).success(roleResourceService.roleResourceVoPageInfo(roleResourceVo));
    }

    @RequestMapping(value = "/addRoleResource", method = RequestMethod.POST)
    public ResultInfo<Void> addRoleResource(@RequestBody RoleResource roleResource){
        try{
            return this.roleResourceService.insert(roleResource) ?
                    ResultInfo.create().success().setMsg("添加成功") :
                    ResultInfo.create().fail("添加失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/deleteRoleResourceById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> deleteRoleResourceById(Long id){
        Preconditions.checkNotNull(id, "Id不能为空");
        try{
            return this.roleResourceService.deleteById(id) ?
                    ResultInfo.create().success().setMsg("删除成功") :
                    ResultInfo.create().fail("删除失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/queryParentRole"}, method = {RequestMethod.GET})
    public ResultInfo<List> queryParentRole(){
        return ResultInfo.create(List.class).success(this.roleService.roleVoList());
    }

    @RequestMapping(value = "/userRoleVoPageInfo", consumes={"application/json"}, method = RequestMethod.POST)
    public ResultInfo<Page> userRoleVoPageInfo(@RequestBody UserRoleVo userRoleVo){
        return ResultInfo.create(Page.class).success(userRoleService.userRoleVoPageInfo(userRoleVo));
    }


    @RequestMapping(value = "/roleVoListForOther", method = RequestMethod.POST)
    public ResultInfo<List> roleVoListForOther(@RequestBody UserRoleVo userRoleVo){
        return ResultInfo.create(List.class).success(roleService.roleVoListForOther(userRoleVo));
    }

    @RequestMapping(value = "/menuVoListForOther", method = RequestMethod.POST)
    public ResultInfo<List> menuVoListForOther(@RequestBody RoleResourceVo roleResourceVo){
        return ResultInfo.create(List.class).success(menuService.menuVoListForOther(roleResourceVo));
    }

    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public ResultInfo<Void> addUserRole(@RequestBody UserRole userRole){
        try{
            return this.userRoleService.insert(userRole) ?
                    ResultInfo.create().success().setMsg("添加成功") :
                    ResultInfo.create().fail("添加失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public ResultInfo<Void> addMenu(@RequestBody Menu menu){
        try{
            return this.menuService.insert(menu) ?
                    ResultInfo.create().success().setMsg("添加成功") :
                    ResultInfo.create().fail("添加失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = "/deleteMenuById", method = RequestMethod.POST)
    public ResultInfo<Void> deleteMenuById(Long id){
        Preconditions.checkNotNull(id,"Id不能为空");
        try{
            return this.menuService.deleteById(id) ?
                    ResultInfo.create().success().setMsg("删除成功") :
                    ResultInfo.create().fail("删除失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = "/deleteUserRoleById", method = RequestMethod.POST)
    public ResultInfo<Void> deleteUserRoleById(Long id){
        Preconditions.checkNotNull(id,"Id不能为空");
        try{
            return this.userRoleService.deleteById(id) ?
                    ResultInfo.create().success().setMsg("删除成功") :
                    ResultInfo.create().fail("删除失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/updateUserRoleById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> updateUserRoleById(@RequestBody UserRoleVo userRoleVo){
        Preconditions.checkNotNull(userRoleVo.getId(), "Id不能为空");
        try{
            return this.userRoleService.updateById(userRoleVo) ?
                    ResultInfo.create().success().setMsg("更新成功") :
                    ResultInfo.create().fail("更新失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/queryParentDictionary"}, method = {RequestMethod.POST})
    public ResultInfo<List> queryParentDictionary(@RequestBody DictionaryVo dictionaryVo){
        return ResultInfo.create(List.class).success(this.dictionaryService.parentDictionaryVoList(dictionaryVo));
    }

    @RequestMapping(value = "/dictionaryVoPageInfo", method = RequestMethod.POST)
    public ResultInfo<Page> dictionaryVoPageInfo(@RequestBody DictionaryVo dictionaryVo){
        return ResultInfo.create(Page.class).success(this.dictionaryService.dictionaryVoPageInfo(dictionaryVo));
    }

    @RequestMapping(value = {"/addDictionary"}, method = {RequestMethod.POST})
    public  ResultInfo<Void> addDictionary(@RequestBody Dictionary dictionary){
        try{
            return this.dictionaryService.insert(dictionary) ?
                    ResultInfo.create().success().setMsg("添加成功") :
                    ResultInfo.create().fail("添加失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/deleteDictionaryById"}, method = {RequestMethod.GET})
    public ResultInfo<Void> deleteDictionaryById(Long id){
        Preconditions.checkNotNull(id, "Id不能为空");
        try{
            return this.dictionaryService.deleteById(id) ?
                    ResultInfo.create().success().setMsg("删除成功") :
                    ResultInfo.create().fail("删除失败");
        }catch (Exception e){
            logger.error("Error", e);
            throw new RuntimeException(e.getCause());
        }
    }

    @RequestMapping(value = {"/updateDictionaryById"}, method = {RequestMethod.POST})
    public ResultInfo<Void> updateDictionaryById(@RequestBody Dictionary dictionary){

        Preconditions.checkNotNull(dictionary.getId(), "Id不能为空");
        System.out.println("alkjsadlkfjasldkjalsdf");
        return this.dictionaryService.updateById(dictionary) ?
                ResultInfo.create().success().setMsg("更新成功") :
                ResultInfo.create().fail("更新失败");
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/listBran" ,method = RequestMethod.POST)
    public ResultInfo<Page> menuVoPageInfo(@RequestBody BranchInfoVo branchInfoVo){
        return ResultInfo.create(Page.class).success(this.branchInfoService.getBranchs(branchInfoVo));
    }
    
    @RequestMapping(value = "/addBran" ,method = RequestMethod.POST)
    public ResultInfo<Void> addBranch(@RequestBody BranchInfo branchInfo)
    {
    	try{
    		return this.branchInfoService.insert(branchInfo) ?
    				ResultInfo.create().success().setMsg("添加成功") :
    					ResultInfo.create().fail("添加失败");
    	}catch (Exception e){
    		logger.error("Error", e);
    		throw new RuntimeException(e.getCause());
    	}
    }
    
    @RequestMapping(value = "/updBran" ,method = RequestMethod.POST)
    public ResultInfo<Void> updBranch(@RequestBody BranchInfo branchInfo)
    {
    	try{
    		this.branchInfoService.updateBranch(branchInfo);
    		return ResultInfo.create().success().setMsg("更新成功");
    	}catch (Exception e){
    		logger.error("Error", e);
    		ResultInfo.create().fail("更新失败");
    		throw new RuntimeException(e.getCause());
    	}
    }
    
    @RequestMapping(value = "/delBran" ,method = RequestMethod.POST)
    public ResultInfo<Void> delBranch(String branCode)
    {
    	try{
    		return this.branchInfoService.deleteByBranCode(branCode) ?
    				ResultInfo.create().success().setMsg("删除成功") :
    					ResultInfo.create().fail("删除失败");
    	}catch (Exception e){
    		logger.error("Error", e);
    		throw new RuntimeException(e.getCause());
    	}
    }
    
    @RequestMapping(value = "/getAllBranchs" ,method = RequestMethod.GET)
    public ResultInfo<List> getAllBranchs() {

    	logger.debug("this is a test");
        return ResultInfo.create(List.class).success(this.branchInfoService.getAllBranch());

    }
}
