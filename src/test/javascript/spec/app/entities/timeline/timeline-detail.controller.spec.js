'use strict';

describe('Controller Tests', function() {

    describe('Timeline Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTimeline, MockContent, MockDay;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTimeline = jasmine.createSpy('MockTimeline');
            MockContent = jasmine.createSpy('MockContent');
            MockDay = jasmine.createSpy('MockDay');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Timeline': MockTimeline,
                'Content': MockContent,
                'Day': MockDay
            };
            createController = function() {
                $injector.get('$controller')("TimelineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'weareholidaysApp:timelineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
