(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripSettingsDetailController', TripSettingsDetailController);

    TripSettingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TripSettings'];

    function TripSettingsDetailController($scope, $rootScope, $stateParams, previousState, entity, TripSettings) {
        var vm = this;

        vm.tripSettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:tripSettingsUpdate', function(event, result) {
            vm.tripSettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
