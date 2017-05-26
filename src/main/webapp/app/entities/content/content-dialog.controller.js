(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('ContentDialogController', ContentDialogController);

    ContentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Content', 'Media', 'Album', 'CheckIn'];

    function ContentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Content, Media, Album, CheckIn) {
        var vm = this;

        vm.content = entity;
        vm.clear = clear;
        vm.save = save;
        vm.media = Media.query({filter: 'content-is-null'});
        $q.all([vm.content.$promise, vm.media.$promise]).then(function() {
            if (!vm.content.mediaId) {
                return $q.reject();
            }
            return Media.get({id : vm.content.mediaId}).$promise;
        }).then(function(media) {
            vm.media.push(media);
        });
        vm.albums = Album.query({filter: 'content-is-null'});
        $q.all([vm.content.$promise, vm.albums.$promise]).then(function() {
            if (!vm.content.albumId) {
                return $q.reject();
            }
            return Album.get({id : vm.content.albumId}).$promise;
        }).then(function(album) {
            vm.albums.push(album);
        });
        vm.checkins = CheckIn.query({filter: 'content-is-null'});
        $q.all([vm.content.$promise, vm.checkins.$promise]).then(function() {
            if (!vm.content.checkInId) {
                return $q.reject();
            }
            return CheckIn.get({id : vm.content.checkInId}).$promise;
        }).then(function(checkIn) {
            vm.checkins.push(checkIn);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.content.id !== null) {
                Content.update(vm.content, onSaveSuccess, onSaveError);
            } else {
                Content.save(vm.content, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:contentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
