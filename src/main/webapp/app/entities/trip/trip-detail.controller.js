(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripDetailController', TripDetailController);

    TripDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trip', 'TripSummary', 'TripSettings', 'Day', 'TripPeople', 'User', 'Coupon'];

    function TripDetailController($scope, $rootScope, $stateParams, previousState, entity, Trip, TripSummary, TripSettings, Day, TripPeople, User, Coupon) {
        var vm = this;

        vm.trip = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:tripUpdate', function(event, result) {
            vm.trip = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
