(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TimelineDetailController', TimelineDetailController);

    TimelineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Timeline', 'Content', 'Day'];

    function TimelineDetailController($scope, $rootScope, $stateParams, previousState, entity, Timeline, Content, Day) {
        var vm = this;

        vm.timeline = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:timelineUpdate', function(event, result) {
            vm.timeline = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
