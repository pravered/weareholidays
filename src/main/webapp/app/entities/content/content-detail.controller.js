(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('ContentDetailController', ContentDetailController);

    ContentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Content', 'Media', 'Album', 'CheckIn'];

    function ContentDetailController($scope, $rootScope, $stateParams, previousState, entity, Content, Media, Album, CheckIn) {
        var vm = this;

        vm.content = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:contentUpdate', function(event, result) {
            vm.content = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
