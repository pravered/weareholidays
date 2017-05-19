(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripSummaryDetailController', TripSummaryDetailController);

    TripSummaryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TripSummary'];

    function TripSummaryDetailController($scope, $rootScope, $stateParams, previousState, entity, TripSummary) {
        var vm = this;

        vm.tripSummary = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:tripSummaryUpdate', function(event, result) {
            vm.tripSummary = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
