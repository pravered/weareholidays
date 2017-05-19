(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripPeopleDetailController', TripPeopleDetailController);

    TripPeopleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TripPeople'];

    function TripPeopleDetailController($scope, $rootScope, $stateParams, previousState, entity, TripPeople) {
        var vm = this;

        vm.tripPeople = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:tripPeopleUpdate', function(event, result) {
            vm.tripPeople = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
