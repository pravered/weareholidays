(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('AlbumDialogController', AlbumDialogController);

    AlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Album', 'Media'];

    function AlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Album, Media) {
        var vm = this;

        vm.album = entity;
        vm.clear = clear;
        vm.save = save;
        vm.media = Media.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.album.id !== null) {
                Album.update(vm.album, onSaveSuccess, onSaveError);
            } else {
                Album.save(vm.album, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:albumUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
