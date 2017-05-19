(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('DayDetailController', DayDetailController);

    DayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Day', 'DaySummary'];

    function DayDetailController($scope, $rootScope, $stateParams, previousState, entity, Day, DaySummary) {
        var vm = this;

        vm.day = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:dayUpdate', function(event, result) {
            vm.day = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
