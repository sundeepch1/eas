/*$('#datepicker').datepicker({
	format : "dd/mm/yyyy",
	autoclose : true,
	todayHighlight : true,
	showOtherMonths : true,
	selectOtherMonths : true,
	autoclose : true,
	changeMonth : true,
	changeYear : true,
	orientation : "button"
});*/

var testInterceptor = function($q, $location, $cookies) {
	return {
		request : function(config) {
			
			/*if ($cookies.get("ok") == "true" && $cookies.get("admin") == "admin") {
				if((config.url =='/admin/viewemployees')
						|| (config.url == '/employee/details/:emploeeId')
						|| (config.url == '/employee/edit/:employeeId')
						|| (config.url == '/employee/adminattendance/:employeeId')
						|| (config.url == '/admin/addemployee')
						|| (config.url == '/employee/viewemployees')){
					return config;
				}
			}*/
			
			if ($cookies.get("ok") == "true" && $cookies.get("emp") == "emp") {
				//$window.alert(config.url);
				if((config.url =='/eas/employeeattendance')
						|| (config.url == '/employee/attendancedate')
						|| (config.url == '/employee/attendancesave')
						|| (config.url == '/eas/attendancedetails')
						|| (config.url == '/employee/empattendancedetails')
						|| (config.url == 'template/pagination/pagination.html')){

					return config;
				}
			}

			if($cookies.get("ok") == "true" && $cookies.get("emp")=="emp"){
				$location.url('/employeeindex');
			}
			
			if($cookies.get("ok")=="true"){
				return config;
			}
			
			if ((config.url == '/eas/indexcarousel')
					|| (config.url == '/eas/login')
					|| (config.url == '/eas/adminlogin')
					|| (config.url == '/eas/register')
					|| (config.url == '/eas/employeelogin')
					|| (config.url == '/eas/forgetpassword')
					|| (config.url == '/eas/resetpassword')
					|| (config.url == '/employee/adminlogin')
					|| (config.url == '/employee/employeelogin')
					|| (config.url == '/employee/register')
					|| (config.url == '/employee/forgetpassword')
					|| (config.url == '/employee/resetpassword/:id')) {
				return config;
			}

			if ($cookies.get("ok") == undefined) {
				$location.url("/login");
			}

			return config;
		},

		requestError : function(rejection) {
			console.log(rejection);
			// Contains the data about the error on the request and return the
			// promise rejection.
			return $q.reject(rejection);
		},

		response : function(response) {
			/*
			 * console.log('data for ' + result.data.name + ' received'); //If
			 * some manipulation of result is required before assigning to
			 * scope. result["testKey"] = 'testValue'; console.log('request
			 * completed');
			 */
			if (response.data == "unauthoriosed") {
				$location.url("/login");
			}
			return response;
		},

		responseError : function(response) {
			console.log('response error started...');

			// Check different response status and do the necessary actions 400,
			// 401, 403,401, or 500 eror
			return $q.reject(response);
		}
	}
}

/*
 * angular.module('myModule').config(['$httpProvider', function($httpProvider) {
 * $httpProvider.interceptors.push('PathInterceptor'); }]);
 */
var myApp = angular
		.module("myModule", [ "ngRoute", "ui.bootstrap", "ngCookies" ])
		.config(function($routeProvider, $httpProvider) {

			$httpProvider.interceptors.push(testInterceptor);

			$routeProvider.when("/", {
				templateUrl : "/eas/indexcarousel"
			}).when("/login", {
				templateUrl : "/eas/login"
			}).when("/login/adminlogin", {
				templateUrl : "/eas/adminlogin",
				controller : "adminLoginController"
			}).when("/login/employeelogin", {
				templateUrl : "/eas/employeelogin",
				controller : "employeeLoginController"
			}).when("/register", {
				templateUrl : "/eas/register",
				controller : "registerController"
			}).when("/forgetpassword", {
				templateUrl : "/eas/forgetpassword",
				controller : "forgetPasswordController"
			}).when("/adminindex", {
				templateUrl : "/eas/adminindex"
			}).when("/employeeindex", {
				templateUrl : "/eas/employeeindex"
			}).when("/resetpassword/:resetPasswordId", {
				templateUrl : "/eas/resetpassword",
				controller : "resetPasswordController"
			}).when("/admin/viewemployees", {
				templateUrl : "/eas/viewemployees",
				controller : "viewEmployeesController"
			}).when("/employee/details/:employeeId", {
				templateUrl : "/eas/employeedetails",
				controller : "employeeDetailsController"
			}).when("/employee/edit/:employeeId", {
				templateUrl : "/eas/employeeedit",
				controller : "employeeEditController"
			}).when("/employee/attendance", {
				templateUrl : "/eas/employeeattendance",
				controller : "attendanceController"
			}).when("/employee/attendanceempdetails", {
				templateUrl : "/eas/attendancedetails",
				controller : "attendanceDetailsEmployeeController"
			}).when("/employee/adminattendance/:employeeId", {
				templateUrl : "/eas/adminattendancedetails",
				controller : "attendanceDetailsAdminController"
			}).when("/employee/attendanceedit/:employeeId", {
				templateUrl : "/eas/attendanceedit",
				controller : "attendanceEditController"
			}).when("/admin/addemployee", {
				templateUrl : "/eas/addemployee",
				controller : "addEmployeeController"
			}).when("/admin/attendance", {
				templateUrl : "/admin/attendance",
				controller : "adminAttendanceController"
			}).otherwise({
				redirectTo : "/"
			})
			// $locationProvider.html5Mode(true);
		}).controller("adminAttendanceController",function($scope, $http, $rootScope, $location) {
			$http({
				method : 'GET',
				url : '/employee/attendancedate',
				headers : {
					'Content-Type' : 'application/json'
				}
			}).then(function(response) {
				$scope.attendance = response.data;
			}, function errorCallback(response) {
				alert("error");
			});
			
			$scope.arrivalHours = "09";
			$scope.arrivalMinutes ="00";
			$scope.arrivalAmPm = "AM";
			$scope.departureHours = "06";
			$scope.departureMinutes ="00";
			$scope.departureAmPm = "PM";
			
			$scope.attendanceSave = function(isValid) {

				if (isValid) {
					var arrivalTotalHours = $scope.arrivalHours;
					var departureTotalHours = $scope.departureHours;
					var arrivalTotalMinutes = $scope.arrivalMinutes;
					var departureTotalMinutes = $scope.departureTotalMinutes;

					if ($scope.arrivalAmPm == 'PM')
						var arrivalTotalHours = arrivalTotalHours + 12;
					if ($scope.departureAmPm == 'PM')
						var departureTotalHours = departureTotalHours + 12;

					var totalDurationHours = departureTotalHours
							- arrivalTotalHours;

					var totalDurationMinutes = 0;

					if (arrivalTotalMinutes < departureTotalMinutes) {
						totalDurationMinutes = arrivalTotalMinutes + 60
								- departureTotalMinutes;
						totalDurationHours = totalDurationHours - 1;
					}

					if (totalDurationHours < 0) {
						totalDurationHours = 24 - totalDurationHours;
					}

					totalDurationMinutes = arrivalTotalMinutes
							- departureTotalMinutes;

					var data = {
						'attendanceDate' : $scope.attendanceDate,
						'arrivalHours' : $scope.arrivalHours,
						'arrivalMinutes' : $scope.arrivalMinutes,
						'arrivalAmPm' : $scope.arrivalAmPm,
						'departureHours' : $scope.departureHours,
						'departureMinutes' : $scope.departureMinutes,
						'departureAmPm' : $scope.departureAmPm,
						'durationHours' : totalDurationHours,
						'durationMinutes' : totalDurationMinutes,
						'employeeId' : $rootScope.employeeId
					}

					$http({
						method : 'POST',
						url : '/employee/attendancesave',
						data : data,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).then(function(response) {
						$scope.attendanceSuccess = response.data;
					}, function errorCallback(response) {
						alert("error");
					});
				} else {
					$scope.attendanceValid = "Fill all the required fields";
				}
			}
		})
		.controller(
				"addEmployeeController",
				function($scope, $http, $location, $rootScope) {

					$scope.checkSelection = function() {
						if (!($scope.gender == "" && $scope.gender == undefined))
							$scope.genderMessage = 'Select gender';
						if (!($scope.employeeType == " " && $scope.employeeType == undefined))
							$scope.emoloyeeTypeMessage = 'Select employee type'
					}

					$scope.addEmployee = function(isValid) {
						if (isValid) {
							var data = {
								'firstName' : $scope.firstName,
								'middleName' : $scope.middleName,
								'lastName' : $scope.lastName,
								'gender' : $scope.gender,
								'employeeType' : $scope.employeeType,
								'age' : $scope.age,
								'dateOfBirth' : $scope.dateOfBirth,
								'address' : $scope.address,
								'mobileNumber' : $scope.mobileNumber,
								'emailAddress' : $scope.emailAddress
										.toLowerCase(),
								'password' : $scope.password,
								'confirmPassword' : $scope.confirmPassword
							};

							if ($scope.password == $scope.confirmPassword) {
								$http({
									method : 'POST',
									url : '/employee/addemployee',
									data : data,
									headers : {
										'Content-Type' : 'application/json'
									}
								})
										.then(
												function(response) {
													if (response.data == "s") {
														$rootScope.addEmployeeMessage = "Added successfully";
														$location
																.url('/adminindex');
													} else if (response.data == "a") {
														$scope.mandatoryField = "This email already registered";
													} else if (response.data == "m") {
														$scope.mandatoryField = "Passwords are not matched";
													} else {
														$scope.mandatoryField = 'Fill all the required field except middle name';
													}
												},
												function errorCallback(response) {
													$scope.mandatoryField = "Error occurred, try other time"
												})
							} else {
								$scope.passwordMessage = "Passwords are not matched";
							}
						} else {
							$scope.mandatoryField = 'Fill all the required field except middle name';
						}
					}
				})
		.controller(
				"mainController",
				function($scope, $window, $cookies, $location, $http,
						$rootScope) {

					/*
					 * var str = "Hello world!"; var res = str.substring(1, 4);
					 * $window.alert(res);
					 * 
					 * $cookies.put("username", "k");
					 * $window.alert($cookies.get('username')); var data =
					 * $cookies.getAll('PLAY_SESSION');
					 * 
					 * 
					 * 
					 * var rawCookie = $cookies['PLAY_SESSION'];
					 * $window.alert(rawCookie); var rawData =
					 * rawCookie.substring(rawCookie.indexOf('-') + 1,
					 * rawCookie.length -1); $window.alert(rawData); var session =
					 * {}; _.each(rawData.split("&"), function(rawPair) { var
					 * pair = rawPair.split('='); session[pair[0]] = pair[1];
					 * });
					 */
					/*
					 * $window.localStrorage.setItem("email","sundeep"); var
					 * session = $window.localStorage.getItem("email");
					 * $window.alert(session);
					 */
					if ($cookies.get("ok") == "true") {
						$rootScope.isVisible = true;
					} else {
						$rootScope.isVisible = false;
					}

					$scope.logout = function() {
						$http({
							method : "get",
							url : "/employee/logout"
						}).then(function(response) {
							$cookies.remove("ok");
							$cookies.remove("admin");
							$cookies.remove("emp");
							$rootScope.isVisible = false;
							$location.url("/login");
						}, function errorCallback(response) {
							alert("Error");
						})
					}
				})

		.controller(
				"employeeLoginController",
				function($scope, $http, $rootScope, $location, $cookies) {
					
					if($cookies.get("ok") == "true" && $cookies.get("emp")=="emp"){
						$location.url("/employeeindex");
					}
					$scope.employeeLogin = function(isValid) {
						if (isValid) {
							var data = {
								'emailAddress' : $scope.emailAddress,
								'password' : $scope.password
							};

							$http({
								method : 'POST',
								url : '/employee/employeelogin',
								data : data,
								headers : {
									'Content-Type' : 'application/json'
								}
							})
									.then(
											function(response) {
												if (response.data == "s"
														|| response.data == "a") {

													$cookies.put("ok", "true");
													$cookies.put("emp","emp");
													$rootScope.isVisible = $cookies
															.get("ok");
													// $rootScope.employeeId =
													// response.data.employeeId;
													$location
															.url('/employeeindex');
												} else if (response.data == "n") {
													$scope.invalidCredentials = "This email address does not exist";
												} else {
													$scope.invalidCredentials = "Invalid Credentials";
												}
											},
											function errorCallback(response) {
												alert("error");
											})
						}
					}
				})
		.controller(
				"adminLoginController",
				function($scope, $http, $rootScope, $location, $cookies) {
					
					if($cookies.get("ok") == "true" && $cookies.get("admin") == "admin"){
						$location.url("/adminindex");
					}
					
					$scope.adminLogin = function(isValid) {
						if (isValid) {
							var data = {
								'emailAddress' : $scope.emailAddress,
								'password' : $scope.password
							};

							$http({
								method : 'POST',
								url : '/employee/adminlogin',
								data : data,
								headers : {
									'Content-Type' : 'application/json'
								}
							})
									.then(
											function(response) {
												if (response.data == "s"
														|| response.data == "a") {
													$cookies.put("ok", "true");
													$cookies.put("admin", "admin");
													$rootScope.isVisible = $cookies
															.get("ok");
													// $rootScope.isVisible =
													// true;
													// $rootScope.employeeId =
													// response.data.employeeId;
													$location
															.url('/adminindex');
												} else if (response.data == "n") {
													$scope.invalidCredentials = "This email address does not exist";
												} else {
													$scope.invalidCredentials = "Invalid Credentials";
												}
											},
											function errorCallback(response) {
												alert("error");
											})
						}
					}
				})
		.controller(
				"registerController",
				function($scope, $http, $location, $rootScope) {

					$scope.checkSelection = function() {
						if (!($scope.gender == "" && $scope.gender == undefined))
							$scope.genderMessage = 'Select gender';
						if (!($scope.employeeType == " " && $scope.employeeType == undefined))
							$scope.emoloyeeTypeMessage = 'Select employee type'
					}

					$scope.register = function(isValid) {
						if (isValid) {
							var data = {
								'firstName' : $scope.firstName,
								'middleName' : $scope.middleName,
								'lastName' : $scope.lastName,
								'gender' : $scope.gender,
								'employeeType' : $scope.employeeType,
								'age' : $scope.age,
								'dateOfBirth' : $scope.dateOfBirth,
								'address' : $scope.address,
								'mobileNumber' : $scope.mobileNumber,
								'emailAddress' : $scope.emailAddress
										.toLowerCase(),
								'password' : $scope.password,
								'confirmPassword' : $scope.confirmPassword
							};

							if ($scope.password == $scope.confirmPassword) {
								$http({
									method : 'POST',
									url : '/employee/register',
									data : data,
									headers : {
										'Content-Type' : 'application/json'
									}
								})
										.then(
												function(response) {
													if (response.data == "s") {
														$rootScope.registeredMessage = "Successfully registered";
														$location
																.url('/login/employeelogin');
													} else if (response.data == "a") {
														$scope.mandatoryField = "This email already registered";
													} else if (response.data == "m") {
														$scope.mandatoryField = "Passwords are not matched";
													} else {
														$scope.mandatoryField = 'Fill all the required field except middle name';
													}
												},
												function errorCallback(response) {
													$scope.mandatoryField = "Error occurred, try other time"
												})
							} else {
								$scope.passwordMessage = "Passwords are not matched";
							}
						} else {
							$scope.mandatoryField = 'Fill all the required field except middle name';
						}
					}
				})
		.controller("forgetPasswordController", function($scope, $http) {
			$scope.forgetPassword = function(isValid) {
				if (isValid) {
					var data = {
						'emailAddress' : $scope.emailAddress
					};

					$http({
						method : 'POST',
						url : '/employee/forgetpassword',
						data : data,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).then(function successCallback(response) {
						$scope.resetMeassge = response.data;
					}, function errorCallback(response) {
						alert("error");
					})
				}
			}
		})
		.controller(
				"attendanceEditController",
				function($scope, $http, $routeParams, $location) {
					$http(
							{
								url : "/employee/adminattendancedetails/"
										+ $routeParams.employeeId,
								method : "get"
							}).then(function(response) {
						$scope.attendances = response.data;
						$scope.totalItems = $scope.attendances.length;
					})

					$scope.viewby = "10";
					$scope.currentPage = 4;
					$scope.itemsPerPage = $scope.viewby;
					$scope.maxSize = 5; // Number of pager buttons to show

					$scope.attendanceEdit = function(isValid) {
						if (isValid) {
							/*
							 * var data = { 'attendances' : $scope.attendances };
							 */
							$http({
								method : "post",
								url : "/employee/attendanceeditsave",
								data : $scope.attendances
							}).then(function(response) {
								if (response.data == "ok") {
									$location.url('/admin/viewemployees');
								}
							}, function errorCallback(response) {
								alert("error");
							})
						} else {
							$scope.attendanceMassage = "Please fill all the required fields"
						}
					}

					$scope.setPage = function(pageNo) {
						$scope.currentPage = pageNo;
					};

					$scope.setItemsPerPage = function(num) {
						$scope.itemsPerPage = num;
						$scope.currentPage = 1; // reset to first page
					}
				})
		.controller(
				"attendanceDetailsAdminController",
				function($scope, $http, $routeParams) {
					$http(
							{
								url : "/employee/adminattendancedetails/"
										+ $routeParams.employeeId,
								method : "get"
							}).then(function(response) {
						$scope.attendances = response.data;
						$scope.totalItems = $scope.attendances.length;
					})

					function attendanceApproved() {
						$http(
								{
									url : "/employee/adminattendancedetails/"
											+ $routeParams.employeeId,
									method : "get"
								}).then(function(response) {
							$scope.attendances = response.data;
							$scope.totalItems = $scope.attendances.length;
						})
					}

					$scope.approved = function() {
						$http({
							method : "post",
							data : $scope.attendances,
							url : "/employee/attendanceapproved"
						}).then(function(response) {
							if (response.data == 'ok') {
								attendanceApproved()
							}
						}, function errorCallback(response) {
							alert("error");
						});
					}
					$scope.firstName = $routeParams.firstName;
					// $scope.lastName = $routeParams.lastName;
					$scope.viewby = "10";

					$scope.currentPage = 4;
					$scope.itemsPerPage = $scope.viewby;
					$scope.maxSize = 5; // Number of pager buttons to show

					$scope.setPage = function(pageNo) {
						$scope.currentPage = pageNo;
					};

					$scope.setItemsPerPage = function(num) {
						$scope.itemsPerPage = num;
						$scope.currentPage = 1; // reset to first page
					}
				})
		.controller("attendanceDetailsEmployeeController",
				function($scope, $http, $rootScope) {
					$http({
						url : "/employee/empattendancedetails"// /2"
						/* + $rootScope.employeeId */,
						method : "get"
					}).then(function(response) {
						$scope.attendances = response.data;
						$scope.totalItems = $scope.attendances.length;
					})

					$scope.viewby = "10";
					$scope.currentPage = 4;
					$scope.itemsPerPage = $scope.viewby;
					$scope.maxSize = 5; // Number of pager buttons to show

					$scope.setPage = function(pageNo) {
						$scope.currentPage = pageNo;
					};

					$scope.pageChanged = function() {
						console.log('Page changed to: ' + $scope.currentPage);
					};

					$scope.setItemsPerPage = function(num) {
						$scope.itemsPerPage = num;
						$scope.currentPage = 1; // reset to first page
					}

				})
		.controller(
				"attendanceController",
				function($scope, $http, $rootScope, $location) {
					$http({
						method : 'GET',
						url : '/employee/attendancedate',
						headers : {
							'Content-Type' : 'application/json'
						}
					}).then(function(response) {
						$scope.attendance = response.data;
					}, function errorCallback(response) {
						alert("error");
					});

					$scope.arrivalHours = "09";
					$scope.arrivalMinutes ="00";
					$scope.arrivalAmPm = "AM";
					$scope.departureHours = "06";
					$scope.departureMinutes ="00";
					$scope.departureAmPm = "PM";
					
					/*var arrivalTotalHours = parseInt($scope.arrivalHours);
					var departureTotalHours = parseInt($scope.departureHours);
					var arrivalTotalMinutes = parseInt($scope.arrivalMinutes);
					var departureTotalMinutes = parseInt($scope.departureMinutes);

					if ($scope.arrivalAmPm == 'PM')
						var arrivalTotalHours = arrivalTotalHours + 12;
					if ($scope.departureAmPm == 'PM')
						var departureTotalHours = departureTotalHours + 12;

					var totalDurationHours = departureTotalHours
							- arrivalTotalHours;

					var totalDurationMinutes = 0;

					if (arrivalTotalMinutes < departureTotalMinutes) {
						totalDurationMinutes = arrivalTotalMinutes + 60
								- departureTotalMinutes;
						totalDurationHours = totalDurationHours - 1;
					}

					if (totalDurationHours < 0) {
						totalDurationHours = 24 - totalDurationHours;
					}
					$scope.totalDurationHoursDisplay = totalDurationHours;
					totalDurationMinutes = arrivalTotalMinutes
					- departureTotalMinutes;
					$scope.totalDurationMinutesDisplay = totalDurationMinutes;
					*/
					$scope.attendanceSave = function(isValid) {

						if (isValid) {
							var data = {
								'attendanceDate' : $scope.attendanceDate,
								'arrivalHours' : $scope.arrivalHours,
								'arrivalMinutes' : $scope.arrivalMinutes,
								'arrivalAmPm' : $scope.arrivalAmPm,
								'departureHours' : $scope.departureHours,
								'departureMinutes' : $scope.departureMinutes,
								'departureAmPm' : $scope.departureAmPm,
								'employeeId' : $rootScope.employeeId
							}

							$http({
								method : 'POST',
								url : '/employee/attendancesave',
								data : data,
								headers : {
									'Content-Type' : 'application/json'
								}
							}).then(function(response) {
								$scope.attendanceSuccess = response.data;
							}, function errorCallback(response) {
								alert("error");
							});
						} else {
							$scope.attendanceValid = "Fill all the required fields";
						}
					}
				})
		.controller(
				"viewEmployeesController",
				function($scope, $http, $route, $location,$window) {
					$http({
						method : 'GET',
						url : '/employee/viewemployees',
						headers : {
							'Content-Type' : 'application/json'
						}
					}).then(function(response) {
						$scope.employees = response.data;
						$scope.totalItems = $scope.employees.length;
					}, function errorCallback(response) {
						alert("error");
					})

					$scope.searchBy = "All";

					$scope.searchEmployee = function() {
						
						var data = {
								'searchBy' : $scope.searchBy,
								'searchText' : $scope.searchText
						}
						
						$http({
							method : 'post',
							url : '/employee/search',
							data : data,
							headers : {
							'Content-Type' : 'application/json'
							}
						}).then(function(response){
							if(response.data != null){
								$scope.employees = response.data;
								$scope.totalItems = $scope.employees.length;
							}else{
								$windeow.alert('No employee found by ' + $scope.searchBy);
							}
						},function errorCallback(response){
							alert("error");
						})
					}

					function refreshAfterDelete() {
						$http({
							method : 'GET',
							url : '/employee/viewemployees',
							headers : {
								'Content-Type' : 'application/json'
							}
						}).then(function(response) {
							$scope.employees = response.data;
							$scope.totalItems = $scope.employees.length;
						}, function errorCallback(response) {
							alert("error");
						})
					}

					$scope.viewby = "10";
					$scope.currentPage = 4;
					$scope.itemsPerPage = $scope.viewby;
					$scope.maxSize = 5; // Number of pager buttons to show

					$scope.setPage = function(pageNo) {
						$scope.currentPage = pageNo;
					};

					$scope.setItemsPerPage = function(num) {
						$scope.itemsPerPage = num;
						$scope.currentPage = 1; // reset to first page
					}

					$scope.deleteEmployee = function(employeeId) {
						$http({
							url : "/employee/employeedelete/" + employeeId,
							method : 'get'
						}).then(function(response) {
							refreshAfterDelete();
						}, function(response) {
							alert("error");
						})
					}
				})
		.controller(
				"employeeDetailsController",
				function($scope, $http, $routeParams) {
					$http(
							{
								url : "/employee/employeedetails/"
										+ $routeParams.employeeId,
								method : "get",
							}).then(function(response) {
						$scope.employee = response.data;
					})
				})
		.controller(
				"employeeEditController",
				function($scope, $http, $routeParams, $location) {
					$http(
							{
								url : "/employee/employeeedit/"
										+ $routeParams.employeeId,
								method : "get",
							}).then(function(response) {
						$scope.employee = response.data;
					})

					$scope.save = function() {
						$http({
							method : 'POST',
							url : '/employee/employeesave',
							data : $scope.employee
						}).then(function(response) {
							$location.url('/admin/viewemployees')
						}, function() {

						})
					}
				})
		.controller(
				"resetPasswordController",
				function($scope, $http, $location, $routeParams) {
					$scope.resetPassword = function(isValid) {
						if (isValid) {
							var data = {
								'password' : $scope.password,
								'confirmPassword' : $scope.confirmPassword
							};
							if ($scope.password == $scope.confirmPassword) {
								$http(
										{
											method : 'post',
											url : '/employee/resetpassword/'
													+ $routeParams.resetPasswordId,
											data : data,
											headers : {
												'Content-Type' : 'application/json'
											}
										})
										.then(
												function(response) {
													if (response.data == "T") {
														$scope.expired = "Your password is successfully changed";
													} else {
														$scope.expired = "Your reset password link is expired";
													}
												},
												function errorCallback(response) {
													$scope.expired = "Something error occured, Try after othertime"
												})
							} else {
								$scope.expired = "Passwords are not matched";
							}
						}
					}
				})
