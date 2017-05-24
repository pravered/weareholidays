(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('CheckInDetailController', CheckInDetailController);

    CheckInDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CheckIn'];

    function CheckInDetailController($scope, $rootScope, $stateParams, previousState, entity, CheckIn) {
        var vm = this;

        vm.checkIn = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:checkInUpdate', function(event, result) {
            vm.checkIn = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
