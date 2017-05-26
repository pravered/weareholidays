(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TimelineDialogController', TimelineDialogController);

    TimelineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Timeline', 'Content', 'Day'];

    function TimelineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Timeline, Content, Day) {
        var vm = this;

        vm.timeline = entity;
        vm.clear = clear;
        vm.save = save;
        vm.contents = Content.query({filter: 'timeline-is-null'});
        $q.all([vm.timeline.$promise, vm.contents.$promise]).then(function() {
            if (!vm.timeline.contentId) {
                return $q.reject();
            }
            return Content.get({id : vm.timeline.contentId}).$promise;
        }).then(function(content) {
            vm.contents.push(content);
        });
        vm.days = Day.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timeline.id !== null) {
                Timeline.update(vm.timeline, onSaveSuccess, onSaveError);
            } else {
                Timeline.save(vm.timeline, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:timelineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
