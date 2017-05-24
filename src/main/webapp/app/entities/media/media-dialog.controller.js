(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('MediaDialogController', MediaDialogController);

    MediaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Media', 'Album'];

    function MediaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Media, Album) {
        var vm = this;

        vm.media = entity;
        vm.clear = clear;
        vm.save = save;
        vm.albums = Album.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.media.id !== null) {
                Media.update(vm.media, onSaveSuccess, onSaveError);
            } else {
                Media.save(vm.media, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:mediaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
