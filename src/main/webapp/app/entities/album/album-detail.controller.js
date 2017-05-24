(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('AlbumDetailController', AlbumDetailController);

    AlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Album', 'Media'];

    function AlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, Album, Media) {
        var vm = this;

        vm.album = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:albumUpdate', function(event, result) {
            vm.album = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
