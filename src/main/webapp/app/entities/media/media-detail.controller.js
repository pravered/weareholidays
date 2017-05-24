(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('MediaDetailController', MediaDetailController);

    MediaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Media', 'Album'];

    function MediaDetailController($scope, $rootScope, $stateParams, previousState, entity, Media, Album) {
        var vm = this;

        vm.media = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:mediaUpdate', function(event, result) {
            vm.media = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
