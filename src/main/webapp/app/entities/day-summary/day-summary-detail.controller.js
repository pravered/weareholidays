(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('DaySummaryDetailController', DaySummaryDetailController);

    DaySummaryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DaySummary'];

    function DaySummaryDetailController($scope, $rootScope, $stateParams, previousState, entity, DaySummary) {
        var vm = this;

        vm.daySummary = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:daySummaryUpdate', function(event, result) {
            vm.daySummary = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
