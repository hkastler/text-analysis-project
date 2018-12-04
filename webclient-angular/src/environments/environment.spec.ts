import * as dev from './environment';
import * as prod from './environment.prod';


describe('Environment', () => {

    it('should create dev', () => {
        expect(dev).toBeTruthy();
        expect(dev.environment.production).toBe(false); 
    });

    it('should create prod', () => {
        expect(prod).toBeTruthy();
        expect(prod.environment.production).toBe(true);
        
    });
});