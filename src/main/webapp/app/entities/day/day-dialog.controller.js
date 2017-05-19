(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('DayDialogController', DayDialogController);

    DayDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Day', 'DaySummary'];

    function DayDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Day, DaySummary) {
        var vm = this;

        vm.day = entity;
        vm.clear = clear;
        vm.save = save;
        vm.daysummaries = DaySummary.query({filter: 'day-is-null'});
        $q.all([vm.day.$promise, vm.daysummaries.$promise]).then(function() {
            if (!vm.day.daySummaryId) {
                return $q.reject();
            }
            return DaySummary.get({id : vm.day.daySummaryId}).$promise;
        }).then(function(daySummary) {
            vm.daysummaries.push(daySummary);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.day.id !== null) {
                Day.update(vm.day, onSaveSuccess, onSaveError);
            } else {
                Day.save(vm.day, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:dayUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
