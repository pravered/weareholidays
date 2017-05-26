'use strict';

describe('Controller Tests', function() {

    describe('Content Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContent, MockMedia, MockAlbum, MockCheckIn;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContent = jasmine.createSpy('MockContent');
            MockMedia = jasmine.createSpy('MockMedia');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockCheckIn = jasmine.createSpy('MockCheckIn');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Content': MockContent,
                'Media': MockMedia,
                'Album': MockAlbum,
                'CheckIn': MockCheckIn
            };
            createController = function() {
                $injector.get('$controller')("ContentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'weareholidaysApp:contentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
