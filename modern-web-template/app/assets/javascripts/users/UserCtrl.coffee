
class UserCtrl

    constructor: (@$log, @$location, @UserService) ->
        @$log.debug "constructing UserController"
        @users = []
        @userid = 0
        @radioModel = 0
        @getAllUsers()

    getAllUsers: () ->
        @$log.debug "getAllUsers()"

        @UserService.listUsers()
        .then(
            (data) =>
                @$log.debug "Promise returned #{data.length} Users"
                @users = data
            ,
            (error) =>
                @$log.error "Unable to get Users: #{error}"
            )

    deleteUser: () ->
        @$log.debug "deleteUser()"
        @user = @users[@userid]
        @$log.debug "User to be deleted is" + @user
        @UserService.deleteUser(@user)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data}"
                @$log.debug "Ret is " + data
                @getAllUsers()
            ,
            (error) =>
                @$log.error "Unable to delete User: #{error}"
            )   
            
    deleteUserArg: (auser) ->
        @$log.debug "deleteUserArg()"
        @$log.debug "User to be deleted is" + auser
        @UserService.deleteUser(auser)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data}"
                @$log.debug "Ret is " + data
                @getAllUsers()
            ,
            (error) =>
                @$log.error "Unable to delete User: #{error}"
            )                        


controllersModule.controller('UserCtrl', UserCtrl)

